import React, {useContext} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import styles from './Auth.module.css'
import {z} from 'zod'
import {zodResolver} from "@hookform/resolvers/zod";
import {observer} from "mobx-react-lite";
import {useNavigate} from "react-router-dom";
import {HOME_ROUTE} from "../navigation/routes";
import {Context} from "../index";

const schema = z.object({
    email: z.string().email(),
    password: z.string().min(4)
})

type FormFields = z.infer<typeof schema>
const Auth = observer(() => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate()
    const {
        register,
        handleSubmit,
        setError,
        formState: {
            errors,
            isSubmitting
        }
    } = useForm<FormFields>({
        resolver: zodResolver(schema)
    })
    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        try {
            if(data && data.email && data.password){
                await userStore.login(data.email, data.password)
                navigate(HOME_ROUTE, {replace: true})
            }
        } catch (e: any) {
            setError('root', { type: 'custom', message: e.response?.data?.message })
        }
    }

    return (
        <div className={styles.login__wrapper}>
            <form className={styles.login__content} onSubmit={handleSubmit(onSubmit)}>
                <div className={styles.login__data}>
                    <input {...register("email", {
                        required: true,
                    })}
                           type="text"
                           placeholder="Email"/>
                    {errors.email && (
                        <div>{errors.email.message}</div>
                    )}
                    <input {...register('password', {
                        required: true,
                    })}
                           type="password"
                           placeholder="Password"/>
                    {errors.password && (
                        <div>{errors.password.message}</div>
                    )}
                </div>

                <button type="submit" disabled={isSubmitting}>{isSubmitting ? 'Loading...' : 'Log in'}</button>
                {errors.root && (
                    <span>{errors.root.message}</span>
                )}
            </form>
        </div>
    );
});

export default Auth;