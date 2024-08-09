import styles from './navigation.module.css'
import homeIcon from '@/shared/icons/home.png'
import searchIcon from '@/shared/icons/search.png'
import {HOME_ROUTE, SEARCH_ROUTE} from "@/shared/consts";
import {useNavigate} from "react-router-dom";

export const Navigation = () => {
    const navigate = useNavigate()
    return (
        <div className={styles.navigation__wrapper}>

            <div className={styles.nav__block}>
                <div className={styles.nav_title} onClick={() => navigate(HOME_ROUTE)}>
                    <img style={{width:26}} src={homeIcon} alt=""/>
                    <span>Home</span>
                </div>
                <div className={styles.nav_title} onClick={() => navigate(SEARCH_ROUTE)}>
                    <img style={{width:26}} src={searchIcon} alt=""/>
                    <span>Search</span>
                </div>
            </div>
        </div>
    );
};

