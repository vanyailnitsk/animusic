import React, {useState} from 'react';
import {z} from "zod";
import {SubmitHandler, useForm} from "react-hook-form";
import {zodResolver} from "@hookform/resolvers/zod";
import {useParams} from "react-router-dom";
import styles from './style.module.css'
import {addSoundtrack} from "../../services/api/tracks";

const allowedExtensions = ['.png', '.jpg', '.jpeg', '.webp'];
const schema = z.object({
    audioFile: z
        .instanceof(FileList)
        .refine((file) => {
            return file[0] && file[0].name;
        }, {
            message: "Invalid file extension. '.mp3' is expected."
        }),
    originalTitle: z.string().min(1),
    animeTitle: z.string().min(1),
    duration: z.string().optional(),
    trackImage: z.instanceof(FileList).refine((file) => {
        if (file[0] && file[0].name) {
            return allowedExtensions.some((ext) => file[0].name.toLowerCase().endsWith(ext))
        }
    }, {
        message: "Invalid file extension"
    })

})
type FormFields = z.infer<typeof schema>
const AddSoundtrack = () => {
    const {id} = useParams()
    const [testImage, setTestImage] = useState<string>('')
    const {
        register,
        handleSubmit,
        formState: {
            errors,
            isSubmitting
        }
    } = useForm<FormFields>({
        resolver: zodResolver(schema)
    })
    const onSubmit: SubmitHandler<FormFields> = async (data, e) => {
        if (id) {
            const addSoundtrackFormData = new FormData()
            addSoundtrackFormData.append('audio', data.audioFile[0])
            addSoundtrackFormData.append('image', data.trackImage[0])
            addSoundtrackFormData.append('originalTitle', data.originalTitle)
            addSoundtrackFormData.append('animeTitle', data.animeTitle)
            addSoundtrackFormData.append('albumId', id)
            addSoundtrackFormData.append('duration', data.duration || "null")
            try {
                await addSoundtrack(addSoundtrackFormData)
                window.location.reload()
            } catch (e) {
                console.log(e)
            }
        }
    }
    const handleTrackImageChange = (e: React.ChangeEvent<HTMLInputElement>) => {
        e.preventDefault()
        const fileList = e.target.files;
        if (fileList && fileList.length > 0) {
            const file = fileList[0];
            const reader = new FileReader();
            reader.onload = () => {
                const imageSrc = reader.result as string;
                setTestImage(imageSrc)
            };
            reader.readAsDataURL(file);
        }
    };
    return (
        <form onSubmit={handleSubmit(onSubmit)} className={styles.add__soundtrack__wrapper}>
            <div>Add Soundtrack</div>
            <input {...register('originalTitle', {required: true})} type="text" placeholder='Original title' className={styles.input__wrapper}/>
            {errors.originalTitle && <div>{errors.originalTitle.message}</div>}
            <input {...register('animeTitle', {required: true})} type="text" placeholder='Anime title' className={styles.input__wrapper}/>
            {errors.animeTitle && <div>{errors.animeTitle.message}</div>}
            <input {...register('duration')} type="text" placeholder='Duration' className={styles.input__wrapper}/>
            {errors.duration && <div>{errors.duration.message}</div>}
            <div>Audio file</div>
            <input {...register('audioFile', {required: true})} type="file"/>
            {errors.audioFile && <div>{errors.audioFile.message}</div>}
            <div>Track image file</div>
            <input {...register('trackImage', {required: true})} type="file" onChange={handleTrackImageChange}/>
            {errors.trackImage && <div>{errors.trackImage.message}</div>}
            <div className={styles.test__img}>
                <img src={testImage} alt=""/>
            </div>
            <button disabled={isSubmitting} className={styles.create__button}>{isSubmitting ? "Creating" : 'Create'}</button>
        </form>
    );
};

export default AddSoundtrack;