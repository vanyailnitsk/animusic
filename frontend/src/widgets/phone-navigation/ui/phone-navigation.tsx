import {BottomNavigation, BottomNavigationAction} from "@mui/material";
import {useContext, useState} from "react";
import styles from './phone-navigation.module.css'
import favorites from '@/shared/icons/icons8-избранное-500.png'
import home from '@/shared/icons/icons8-главная-192.png'
import search from '@/shared/icons/icons8-поиск-150.png'
import {useNavigate} from "react-router-dom";
import {enqueueSnackbar, VariantType} from "notistack";
import {COLLECTION} from "@/shared/consts";
import {Context} from "@/main.tsx";
export const PhoneNavigation = () => {
    const {userStore} = useContext(Context)
    const navigate = useNavigate()
    const handleGoHome = () => {
        navigate('/')
    }
    const handleGoToSearch = () => {
        navigate('/search')
    }
    const handleError = (variant:VariantType)  => {
        enqueueSnackbar('You need to sign in!', {variant});
    };
    const handleGoToCollection = () => {
        if(!userStore.isAuth){
            handleError('error')
        }
        else {
            navigate(COLLECTION)
        }
    }
    return (
            <BottomNavigation
                className={styles.navigation__wrapper}
            >
                <BottomNavigationAction label="Home" icon={IconGeneration(home)} onClick={handleGoHome}/>
                <BottomNavigationAction label="Favorites" icon={IconGeneration(favorites)} onClick={handleGoToCollection}/>
                <BottomNavigationAction label="Search" icon={IconGeneration(search)} onClick={handleGoToSearch}/>
            </BottomNavigation>
    );
};

const IconGeneration = (url) => {
    return <img src={url} alt="" className={styles.navigation__icon}/>
}
