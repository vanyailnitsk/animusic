import {useContext} from 'react';
import styles from './page-wrapper.module.css'
import avatar from "../../icons/avatar.jpeg";
import {Context} from "@/main.tsx";


export const PageWrapper = ({page}) => {
    const {userStore} = useContext(Context)
    const logoutPic = () => {
        userStore.logout()
    }
    return (
        <div className={styles.page__wrapper}>
            <div className={styles.profile__picture__wrapper}>
                <img src={avatar} alt="" style={{width:'100%',display:'block'}} onClick={() => logoutPic()}/>
            </div>
            {page}
        </div>
    );
};

