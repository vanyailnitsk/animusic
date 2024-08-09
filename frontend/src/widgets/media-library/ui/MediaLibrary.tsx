import styles from './mediaLibrary.module.css'
import {FavoriteTracks} from "@/shared/ui";

export const MediaLibrary = () => {

    return (
        <div className={styles.media__library__wrapper}>
            <FavoriteTracks/>
        </div>
    );
};

