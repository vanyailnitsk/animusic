import {useContext} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import styles from './sign-up.module.css'
import {z} from 'zod'
import {zodResolver} from "@hookform/resolvers/zod";
import {observer} from "mobx-react-lite";
import logo from "@/shared/icons/logo.ico";
import {useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {HOME_ROUTE, LOGIN} from "@/shared/consts";

const schema = z.object({
    username: z.string(),
    email: z.string().email(),
    password: z.string().min(4)
})

type FormFields = z.infer<typeof schema>
export const SignUp = observer(() => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate()
    const {
        register,
        handleSubmit,
        setError,
        formState: {errors, isSubmitting}
    } = useForm<FormFields>({resolver: zodResolver(schema)})
    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        try {
            await userStore.registration(data.username, data.email, data.password)
            navigate(HOME_ROUTE, {replace: true})
        } catch (e: any) {
            setError('root', {type: 'custom', message: e.status === 500 ? e.message : e.response.data.error})
        }
    }
    return (
        <div className={styles.signup__wrapper}>
            <form className={styles.signup__content} onSubmit={handleSubmit(onSubmit)}>
                <div className={styles.logo}>
                    <img src={logo} alt=""/>
                </div>
                <div className={styles.signup__data}>
                    <input {...register("username", {
                        required: true,
                    })}
                           type="text"
                           placeholder="Username"/>
                    {errors.username && (
                        <div>{errors.username.message}</div>
                    )}
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
                {errors.root?.message && (
                    <span>{errors.root.message}</span>
                )}
                <button type="submit" disabled={isSubmitting}>{isSubmitting ? 'Loading...' : 'Sign up'}</button>
                <div className={styles.redirection}>
                    <span>Already have an account?</span>
                    <span onClick={() => navigate(LOGIN)} style={{cursor: "pointer"}}> Sign in</span>
                </div>
            </form>
        </div>
    );
});

