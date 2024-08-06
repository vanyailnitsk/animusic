import React from 'react';
import styles from './style.module.css';
import Skeleton from "react-loading-skeleton";

const AlbumCardSkeleton = () => {
    return (
            <Skeleton className={styles.album__card__skeleton}/>
    );
};

export default AlbumCardSkeleton;