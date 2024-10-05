import {FC, ReactNode, useContext} from 'react';
import styles from './page-wrapper.module.css'
import {Button} from "@/shared/ui";
import {useLocation, useNavigate} from "react-router-dom";
import {Context} from "@/main.tsx";
import {observer} from "mobx-react-lite";

interface PageWrapperProps{
    page:ReactNode
}
export const PageWrapper:FC<PageWrapperProps> = observer(({page}) => {
    const {userStore,musicStore} = useContext(Context)
    const navigate = useNavigate()
    const location = useLocation()
    const handleSignIn = () => {
        localStorage.setItem('redirectPath', location.pathname);
        navigate('/sign-in');
    };

    const handleSignUp = () => {
        localStorage.setItem('redirectPath', location.pathname);
        navigate('/sign-up');
    };
    const handleLogout = () => {
        userStore.logout()
        musicStore.fav_tracks = []
    }
    return (
        <div className={styles.page__wrapper}>
            {userStore.isAuth?(
                <Button className={styles.logout} content={'Logout'} onClick={handleLogout}/>
            ):(<div className={styles.auth__actions}>
                <Button className={styles.sign__up} content={'Sign up'} onClick={handleSignUp}/>
                <Button className={styles.sign__in} content={'Sign in'} onClick={handleSignIn}/>
            </div>)
            }
            {page}
        </div>
    );
});

