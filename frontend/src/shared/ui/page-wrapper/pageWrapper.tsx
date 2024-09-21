import {FC, ReactNode} from 'react';
import styles from './page-wrapper.module.css'
import {Button} from "@/shared/ui";
import {useLocation, useNavigate} from "react-router-dom";
import {observer} from "mobx-react-lite";
import {useAppDispatch, useAppSelector} from "@/shared/lib/store";
import {logout, selectUser, selectUserLoading} from "@/entities/user";
import {clearCollection} from "@/entities/music";

interface PageWrapperProps{
    page:ReactNode
}
export const PageWrapper:FC<PageWrapperProps> = observer(({page}) => {
    const user = useAppSelector(selectUser)
    const userLoading = useAppSelector(selectUserLoading);
    const dispatch = useAppDispatch()
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
    const handleLogout = async () => {
        try {
            await dispatch(logout())
            await dispatch(clearCollection())
        }
        catch (e){
            console.log(e)
        }
    }
    return (
        <div className={styles.page__wrapper}>
            {!userLoading && (user?(
                <Button className={styles.logout} content={'Logout'} onClick={handleLogout}/>
            ):(<div className={styles.auth__actions}>
                <Button className={styles.sign__up} content={'Sign up'} onClick={handleSignUp}/>
                <Button className={styles.sign__in} content={'Sign in'} onClick={handleSignIn}/>
            </div>))
            }
            {page}
        </div>
    );
});

