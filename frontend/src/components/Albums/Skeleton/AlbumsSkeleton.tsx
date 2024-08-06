import React from 'react';
import AlbumCardSkeleton from "../../AlbumCard/Skeleton/AlbumCardSkeleton";
import styles from './style.module.css'

const AlbumsSkeleton = () => {
    return (
        <div className={styles.album_skeleton}>
            {[...Array(4)].map((_, index) => (
                <AlbumCardSkeleton key={index} />
            ))}
        </div>
    );
};

export default AlbumsSkeleton;