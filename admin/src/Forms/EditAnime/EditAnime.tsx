import React, {ChangeEvent, MouseEventHandler, useContext, useEffect, useState} from 'react';
import { SubmitHandler, useForm } from "react-hook-form";
import { z } from 'zod';
import ColorThief from 'colorthief';
import styles from './style.module.css';
import { zodResolver } from "@hookform/resolvers/zod";
import {addAnimeCard, editAnime, editAnimeBanner, getAnimeInfo} from "../../services/api/anime";
import { useParams } from "react-router-dom";
import { IAnime } from "../../models/Anime";
import AnimeCard from "../../components/AnimeCard/AnimeCard";
import AnimeTestCard from "../../components/AnimeTestCard/AnimeTestCard";
import Color from "../../components/ChangeColor/Color/Color";
import { Context } from "../../index";
import ColorList from "../../components/ChangeColor/ChnageColor/ChangeColor";
import ChangeColor from "../../components/ChangeColor/ChnageColor/ChangeColor";

const schema = z.object({
    title: z.string(),
    studio: z.string().optional(),
    releaseYear: z.string().optional(),
    card: z.any().optional(),
    banner: z.any().optional(),
});

type FormFields = z.infer<typeof schema>;

const EditAnime = () => {
    const { id } = useParams();
    const [testCardImage, setTestCardImage] = useState<string | null>(null);
    const { editBannerStore } = useContext(Context)
    const [selectedBannerImage, setSelectedBannerImage] = useState<string | Blob>('')
    const [bannerColors, setBannerColors] = useState<string[]>([]);
    const [animeData, setAnimeData] = useState<IAnime | undefined>();
    const [editingCard, setEditingCard] = useState(false);
    const [selectedCardImage, setSelectedCardImage] = useState<null | File>(null);

    const {
        register,
        setError,
        handleSubmit,
        formState: { errors, isSubmitting },
        setValue,
    } = useForm<FormFields>({
        resolver: zodResolver(schema),
        defaultValues: {
            title: '',
            studio: '',
            releaseYear: '0',
        },
    });

    useEffect(() => {
        getAnimeInfo(id)
            .then(response => {
                setAnimeData(response.data);
                setValue('title', response.data.title);
                if (response.data.studio) {
                    setValue('studio', response.data.studio);
                }
                if (response.data.releaseYear) {
                    setValue('releaseYear', response.data.releaseYear.toString());

                }
            });
    }, []);

    const handleCardFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            setSelectedCardImage(file);
            const reader = new FileReader();
            reader.onload = () => {
                setTestCardImage(reader.result as string);
            };
            reader.readAsDataURL(file);
        }
    };

    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        if (selectedCardImage) {
            const imageFormData = new FormData();
            imageFormData.append('card', selectedCardImage)
            editBannerStore.setCardImg(imageFormData);
        } else {
            editBannerStore.setCardImg(null)
        }
        const editAnimeData = {
            id:id,
            animeData:{
                title: data.title,
                studio: data.studio,
                releaseYear: data.releaseYear,
                folderName:data.title.trim().replace(/\s/g, '')
            }
        }
        if(editBannerStore.card){
            const editCardData = {
                id:id,
                card:editBannerStore.card
            }
            try {
                await addAnimeCard(editCardData)
            }
            catch (e){
                console.log(e)
            }
        }
        if(editBannerStore.bannerImage && editBannerStore.color){
            const bannerFormData = new FormData()
            bannerFormData.append('color', editBannerStore.color)
            bannerFormData.append('banner', selectedBannerImage)
            const editBannerData = {
                id:id,
                banner:bannerFormData
            }
            try {
                await editAnimeBanner(editBannerData)
                window.location.reload()
            }
            catch (e){
                console.log(e)
            }
        }
        try {
            await editAnime(editAnimeData)
        }
        catch (e){
            console.log(e)
        }

    };

    const extractColorsFromImage = (imageSrc: string) => {
        const colorThief = new ColorThief();
        const image = new Image();
        image.crossOrigin = "Anonymous";
        image.src = imageSrc;

        image.onload = () => {
            const colors = colorThief.getPalette(image, 20);
            const hexColors = colors.map(rgbToHex);
            setBannerColors(hexColors);
        };
    };

    const handleEditingCard = () => {
        setEditingCard(!editingCard)
        setSelectedCardImage(null)
        setTestCardImage(null)
    }

    const rgbToHex = (color: number[]) => {
        return "#" + ((1 << 24) + (color[0] << 16) + (color[1] << 8) + color[2]).toString(16).slice(1);
    };

    const handleBannerChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.stopPropagation()
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            setSelectedBannerImage(file);
            const reader = new FileReader();
            reader.onload = () => {
                const imageSrc = reader.result as string;
                extractColorsFromImage(imageSrc)
                editBannerStore.setBannerImage(imageSrc)
            };
            reader.readAsDataURL(file);
        }
    };

    const cancelBannerEditing = (e:any) => {
        e.preventDefault()
        setSelectedBannerImage('')
        editBannerStore.setBannerImage("")
        editBannerStore.setColor('')
    }
    return (
        <form className={styles.edit__anime__wrapper} onSubmit={handleSubmit(onSubmit)}>
            <div>Edit Anime</div>
            <input {...register('title')}
                   type="text"
                   placeholder='Title'
                   className={styles.input__wrapper}
            />
            {errors.title && (
                <div>{errors.title.message}</div>
            )}
            <input {...register('studio')}
                   type="text"
                   placeholder='Studio'
                   className={styles.input__wrapper}
            />
            {errors.studio && (
                <div>{errors.studio.message}</div>
            )}
            <input {...register('releaseYear')}
                   type="text"
                   placeholder='Release Year'
                   className={styles.input__wrapper}
            />
            {errors.releaseYear && (
                <div>{errors.releaseYear.message}</div>
            )}
            {editingCard ? (
                <div>
                    <AnimeTestCard title={animeData?.title} cardImage={testCardImage} />
                    <input {...register('card')} type="file" onChange={handleCardFileChange} />
                    <button onClick={handleEditingCard} style={{ paddingInline: 10, paddingBlock: 2 }}>Close</button>
                </div>
            ) : (
                <div>
                    <AnimeCard card={animeData} />
                    <button onClick={() => setEditingCard(!editingCard)} className={styles.edit__card__button} >Edit card</button>
                </div>
            )}
            <div>
                <span>Change Banner</span>
                <input {...register('banner')} type="file" onChange={handleBannerChange} />
                <ChangeColor colors={bannerColors} store={editBannerStore}/>
                <button onClick={cancelBannerEditing} style={{paddingInline:10,paddingBlock:3}}>Cancel banner change</button>
            </div>
            <button disabled={isSubmitting} className={styles.edit__button}>{isSubmitting ? 'Editing' : 'Edit'}</button>
        </form>
    );
};

export default EditAnime;
