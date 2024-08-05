import React from 'react';
import styles from './style.module.css'
import AnimeCardSkeleton from "../../AnimeCard/Skeleton/AnimeCardSkeleton";

const SkeletonCardList = () => {
    return (
        <div className={styles.skeleton__cardslist__wrapper}>
            {[...Array(12)].map((_, index) => (
                <AnimeCardSkeleton key={index} />
            ))}
        </div>
    );
};

export default SkeletonCardList;