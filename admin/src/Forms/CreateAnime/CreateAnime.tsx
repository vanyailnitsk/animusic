import React, {useState} from 'react';
import styles from './style.module.css'
import {z} from 'zod'
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import AnimeTestCard from "../../components/AnimeTestCard/AnimeTestCard";
import {addAnimeCard, createAnime} from "../../services/api/anime";
const schema = z.object({
    title:z.string().min(1,'Invalid Anime Name'),
    studio:z.string().min(1,'Invalid Studio Name'),
    releaseYear:z.string().min(4).max(4),
    card: z.any().refine(val => val.length > 0, "File is required"),
})
type FormFields = z.infer<typeof schema>
const CreateAnime = () => {
    const [title,setTitle] = useState('')
    const [cardImage, setCardImage] = useState<string | null>(null);
    const [selectedImage, setSelectedImage] = useState<string | Blob>('');
    const {
        register,
        handleSubmit,
        setError,
        formState: {
            errors,
            isSubmitting
        }} = useForm<FormFields>({
        resolver:zodResolver(schema)
    })
    const handleFileChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        const fileList = e.target.files;
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            setSelectedImage(file);
            const reader = new FileReader();
            reader.onload = () => {
                setCardImage(reader.result as string);
            };
            reader.readAsDataURL(file);
        }
    }

    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        const imageFormData = new FormData();
        imageFormData.append('card', selectedImage);
        const animeData = {
            title:data.title,
            studio:data.studio,
            releaseYear:data.releaseYear,
            folderName:title.trim().replace(/\s/g, '')
        }
        try {
            if(animeData){
                const response = await createAnime(JSON.stringify(animeData))
                const data = {
                    card:imageFormData,
                    id:response.data.id
                }
                await addAnimeCard(data)
            }
        }
        catch (e){
            console.log(e)
        }

    }
    return (
        <form onSubmit={handleSubmit(onSubmit)} className={styles.create__anime__wrapper}>
            <div>Create Anime</div>
            <input {...register('title',{required:true})} type='text' placeholder='Title' className={styles.input__wrapper} onChange={(e) => setTitle(e.target.value)}/>
            {errors.title && (
                <div>{errors.title.message}</div>
            )}
            <input {...register('studio',{required:true})} type='text' placeholder='Studio' className={styles.input__wrapper}/>
            {errors.studio && (
                <div>{errors.studio.message}</div>
            )}
            <input {...register('releaseYear',{required:true})} type='text' placeholder='Release Year' className={styles.input__wrapper}/>
            {errors.releaseYear && (
                <div>{errors.releaseYear.message}</div>
            )}
            <div>
                <AnimeTestCard title={title} cardImage={cardImage}/>
                <input {...register('card',{required:true})} type="file" onChange={handleFileChange}/>
            </div>
            <button disabled={isSubmitting} type='submit' className={styles.create__button}>{isSubmitting? 'Creating' : 'Create'}</button>
        </form>
    );
};

export default CreateAnime;