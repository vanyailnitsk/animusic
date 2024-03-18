import React, {useContext} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import styles from './Login.module.css'
import {z} from 'zod'
import {zodResolver} from "@hookform/resolvers/zod";
import {Context} from "../../index";
import logo from '../../icons/logo.ico'
import {observer} from "mobx-react-lite";
import {Navigate, useNavigate} from "react-router-dom";
import {SIGN_UP} from "../../navigation/routes";
const schema = z.object({
    email: z.string().email(),
    password: z.string().min(4)
})

type FormFields = z.infer<typeof schema>
const Login = () => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate()
    const {register, handleSubmit, setError, formState: {errors,isSubmitting}} = useForm<FormFields>({resolver:zodResolver(schema)})
    const onSubmit: SubmitHandler<FormFields> = (data) => {
        userStore.login(data.email, data.password)
    }
    return (
        <div className={styles.login__wrapper}>
            <form className={styles.login__content} onSubmit={handleSubmit(onSubmit)}>
                <div className={styles.logo}>
                    <img src={logo} alt="" />
                </div>
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
                        required:true,
                    })}
                           type="password"
                           placeholder="Password"/>
                    {errors.password && (
                        <div>{errors.password.message}</div>
                    )}
                </div>
                <button type="submit" disabled={isSubmitting}>{isSubmitting? 'Loading...' : 'Log in'}</button>
                <div className={styles.redirection}>
                    <span>Don't have an account yet?</span>
                    <span onClick={() => navigate(SIGN_UP)} style={{cursor:'pointer'}}> Sign Up</span>
                </div>
            </form>
        </div>
    );
};

export default observer(Login);