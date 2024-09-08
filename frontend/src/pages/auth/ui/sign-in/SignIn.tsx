import {useContext, useEffect} from 'react';
import {SubmitHandler, useForm} from "react-hook-form";
import styles from './sign-in.module.css'
import {z} from 'zod'
import {zodResolver} from "@hookform/resolvers/zod";
import logo from '@/shared/assets/icons/logo.ico'
import {observer} from "mobx-react-lite";
import {useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {HOME_ROUTE, SIGN_UP} from "@/shared/consts";
import {useAppDispatch} from "@/shared/lib/store";
import {setIsPlaying} from "@/entities/music";

const schema = z.object({
    email: z.string().email(),
    password: z.string().min(4)
})

type FormFields = z.infer<typeof schema>
export const SignIn = observer(() => {
    const {userStore,musicStore} = useContext(Context)
    const navigate = useNavigate()
    const dispatch = useAppDispatch()
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
    useEffect(() => {
        dispatch(setIsPlaying(false))
    }, []);
    const onSubmit: SubmitHandler<FormFields> = async (data) => {
        if (data && data.email && data.password) {
            try {
                await userStore.login(data.email, data.password)
                const redirectPath = localStorage.getItem('redirectPath')
                if(redirectPath){
                    navigate(redirectPath, {replace: true})
                }
                else{
                    navigate(HOME_ROUTE,{replace: true})
                }
            } catch (e: any) {
                setError('root', {
                    type: 'custom',
                    message: e.request.status === 0 ? e.message : e.response.data.message
                })
            }
        }
    }

    return (
        <div className={styles.login__wrapper}>
            <form className={styles.login__content} onSubmit={handleSubmit(onSubmit)}>
                <div className={styles.logo}>
                    <img src={logo} alt=""/>
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
                <div className={styles.redirection}>
                    <span>Don't have an account yet?</span>
                    <span onClick={() => navigate(SIGN_UP)} style={{cursor: 'pointer'}}> Sign Up</span>
                </div>
            </form>
        </div>
    );
});

