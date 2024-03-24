import React from 'react';
import Skeleton from 'react-loading-skeleton'
import 'react-loading-skeleton/dist/skeleton.css'
import styles from './style.module.css'
const AnimeCardSkeleton = () => {
    return (
        <div>
            <Skeleton className={styles.anime__card__skeleton__wrapper}/>
        </div>
    );
};

export default AnimeCardSkeleton;