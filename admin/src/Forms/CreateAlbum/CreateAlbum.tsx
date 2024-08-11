import React, {useContext, useState} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import {z} from 'zod'
import styles from './style.module.css'
import {zodResolver} from "@hookform/resolvers/zod";
import {useNavigate, useParams} from "react-router-dom";
import {createAlbum} from "../../services/api/albums";
import {Context} from "../../index";
const schema = z.object({
    name:z.enum(['Openings','Endings','Scene songs','Themes']),
})

type FormFields = z.infer<typeof schema>
const CreateAlbum = () => {
    const {id} = useParams()
    const navigate = useNavigate()
    const {contentStore} = useContext(Context)
    const {
        register,
        setError,
        handleSubmit,
        formState: {
            errors,
            isSubmitting
        }
    } = useForm<FormFields>({
        resolver:zodResolver(schema)
    })
    const onSubmit:SubmitHandler<FormFields> = async (data) => {
        const albumData = {
            animeId:id,
            name:data.name,
        }
        try {
            const response = await createAlbum(albumData)
            navigate(`/album-manage/${response.data.id}`)
        }
        catch (e:any){
            setError('root', {message:e.response.data.error})
        }
    }
    return (
        <form className={styles.create__album__wrapper} onSubmit={handleSubmit(onSubmit)}>
            <div>Create Album</div>
            <input {...register('name',{required:true})} type="text" className={styles.input__wrapper} placeholder='Name'/>
            {errors.name &&
                <div>{errors.name.message}</div>
            }
            {errors.root&&
                <div>{errors.root.message}</div>
            }
            <button className={styles.create__button} disabled={isSubmitting}>{isSubmitting? 'Creating' : 'Create'}</button>
        </form>
    );
};

export default CreateAlbum;