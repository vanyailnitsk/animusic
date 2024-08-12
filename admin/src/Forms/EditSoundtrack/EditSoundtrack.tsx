import React, {useContext, useEffect, useRef, useState} from 'react';
import {z} from 'zod'
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import styles from './style.module.css'
import {Context} from "../../index";
import {observer} from "mobx-react";
import {
    editSoundtrackAudioRequest, editSoundtrackImageRequest,
    editSoundtrackInfoRequest,
    getSoundtrackInfo
} from "../../services/api/tracks";
const schema = z.object({
    audioFile: z.any(),

    trackImage: z.any(),

    originalTitle: z.string().optional(),
    animeTitle: z.string().optional(),
    duration: z.string().optional(),
});
export interface soundtrackInfo {
    originalTitle: string | null | undefined
    animeTitle: string | null | undefined
    duration: string | null | undefined
}
type FormFields = z.infer<typeof schema>
const EditSoundtrack = () => {
    const trackImageFileRef = useRef<HTMLInputElement>(null)
    const trackAudioFile = useRef<HTMLInputElement>(null)
    const {editSoundtrack} = useContext(Context)
    const {
        register,
        handleSubmit,
        setValue,
        formState:{
            errors,
            isSubmitting
        }
    }=useForm<FormFields>({
        resolver:zodResolver(schema),
        defaultValues:{
            originalTitle: '',
            animeTitle: '',
            duration: '0'
        }
    })
    useEffect(() => {
        if(trackImageFileRef.current){
            trackImageFileRef.current.value = ''
        }
        if(trackAudioFile.current){
            trackAudioFile.current.value = ''
        }
        if(editSoundtrack.trackId){
            getSoundtrackInfo(editSoundtrack.trackId)
                .then(response => {
                    setValue('originalTitle',response.data.soundtrack.originalTitle)
                    setValue('animeTitle',response.data.soundtrack.animeTitle)
                    setValue('duration',response.data.soundtrack.duration.toString())
                })
        }
    }, [editSoundtrack.trackId]);
    const onSubmit:SubmitHandler<FormFields> = async (data) => {
        await new Promise(resolve => setTimeout(resolve,1000))
        const soundtrackInfo:soundtrackInfo = {
            animeTitle: data.animeTitle || null,
            originalTitle: data.originalTitle || null,
            duration: data.duration || null
        }
        const editTrackInfoData = {
            id:editSoundtrack.trackId,
            data:soundtrackInfo
        }
        try {
            editSoundtrackInfoRequest(editTrackInfoData)
        }
        catch (e){
            console.log(e)
        }
        if(data.audioFile){
            const audioFormData = new FormData()
            audioFormData.append('audio',data.audioFile)
            const editAudioData = {
                id:editSoundtrack.trackId,
                data:audioFormData
            }
            try {
                editSoundtrackAudioRequest(editAudioData)
            }
            catch (e){
                console.log(e)
            }
        }
        if(data.trackImage){
            const imageFormData = new FormData()
            imageFormData.append('image',data.trackImage)
            const editImageData = {
                id:editSoundtrack.trackId,
                data:imageFormData
            }
            try {
                editSoundtrackImageRequest(editImageData)
            }
            catch (e){
                console.log(e)
            }
        }
    }
    const handleTrackImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList){
            setValue('trackImage', fileList[0])
        }
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            const reader = new FileReader();
            reader.onload = () => {
                const imageSrc = reader.result as string;
                editSoundtrack.setTrackImage(imageSrc)
            };
            reader.readAsDataURL(file);
        }
    };
    const handleAudioChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList){
            setValue('audioFile', fileList[0])
        }
    };
    return (
        <form onSubmit={handleSubmit(onSubmit)} className={styles.edit__soundtrack__wrapper}>
            <div>Edit soundtrack</div>
            <input {...register('originalTitle')} type="text" placeholder='Original title' className={styles.input__wrapper}/>
            {errors.originalTitle && <div>{errors.originalTitle.message}</div>}
            <input {...register('animeTitle')} type="text" placeholder='Anime title' className={styles.input__wrapper}/>
            {errors.animeTitle && <div>{errors.animeTitle.message}</div>}
            <input {...register('duration')} type="text" placeholder='Duration' className={styles.input__wrapper}/>
            {errors.duration && <div>{errors.duration.message}</div>}
            <div>Audio file</div>
            <input {...register('audioFile')} type="file" ref={trackAudioFile} onChange={handleAudioChange}/>
            <div>Track image file</div>
            <input {...register('trackImage')} type="file" onChange={handleTrackImageChange} ref={trackImageFileRef}/>
            <button disabled={isSubmitting} className={styles.edit__button}>{isSubmitting ? "Editing" : 'Edit'}</button>
        </form>
    );
};

export default observer(EditSoundtrack);