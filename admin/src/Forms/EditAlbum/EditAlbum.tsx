import React, {useContext, useState} from 'react';
import {z} from "zod";
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {Context} from "../../index";
import ColorThief from "colorthief";
import styles from './style.module.css'
import ChangeColor from "../../components/ChangeColor/ChnageColor/ChangeColor";
import {useParams} from "react-router-dom";
import {updateAlbum} from "../../services/api/albums";
import AddSoundtrack from "../AddSoundtrack/AddSoundtrack";

const schema = z.object({
    card:z.any()
})
type FormFields = z.infer<typeof schema>
const EditAlbum = () => {
    const {editAlbumStore} = useContext(Context)
    const {id} = useParams()
    const [albumColors,setAlbumColors] = useState<string[]>([])
    const [selectedAlbumImg,setSelectedAlbumImg] = useState<null | Blob>(null)
    const {
        register,
        handleSubmit,
        setError,
        formState:{
            errors,
            isSubmitting
        }
    }=useForm<FormFields>({
        resolver:zodResolver(schema)
    })
    const extractColorsFromImage = (imageSrc: string) => {
        const colorThief = new ColorThief();
        const image = new Image();
        image.crossOrigin = "Anonymous";
        image.src = imageSrc;

        image.onload = () => {
            const colors = colorThief.getPalette(image, 20);
            const hexColors = colors.map(rgbToHex);
            setAlbumColors(hexColors)
        };
    };
    const rgbToHex = (color: number[]) => {
        return "#" + ((1 << 24) + (color[0] << 16) + (color[1] << 8) + color[2]).toString(16).slice(1);
    };
    const onSubmit:SubmitHandler<FormFields> = async (data) => {
        const updateAlbumFormData = new FormData()
        if(selectedAlbumImg && editAlbumStore.colorLight){
            updateAlbumFormData.append('imageFile', selectedAlbumImg)
            updateAlbumFormData.append('colorLight',editAlbumStore.colorLight)
            updateAlbumFormData.append('colorDark',editAlbumStore.colorDark)
            const updateAlbumData = {
                id:id,
                updateData:updateAlbumFormData
            }
            try{
                await updateAlbum(updateAlbumData)
            }
            catch (e){
                console.log(e)
            }
        }

    }
    const handleAlbumCardFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            setSelectedAlbumImg(file);
            const reader = new FileReader();
            reader.onload = () => {
                const imageSrc = reader.result as string;
                extractColorsFromImage(imageSrc)
                editAlbumStore.setCard(imageSrc)
            };
            reader.readAsDataURL(file);
        }
    };
    return (
        <form onSubmit={handleSubmit(onSubmit)} className={styles.update__album__wrapper}>
            <div>Edit Album</div>
            <div>
                <input type="file" {...register('card')} onChange={handleAlbumCardFileChange}/>
            </div>
            <ChangeColor colors={albumColors} store={editAlbumStore}/>
            <button disabled={isSubmitting} className={styles.edit__button}>{isSubmitting? 'Editing':'Edit'}</button>
        </form>
    );
};

export default EditAlbum;