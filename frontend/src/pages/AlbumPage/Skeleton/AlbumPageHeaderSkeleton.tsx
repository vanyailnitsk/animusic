import React from 'react';
import styles from "./albumPageHeaderSkeleton.module.css";
import Skeleton from "react-loading-skeleton";

const AlbumPageHeaderSkeleton = () => {
    return (
        <div className={styles.skeleton_content}>
                <Skeleton className={styles.skeleton__image}/>
            <div className={styles.skeleton__main__info}>
                <Skeleton style={{width:100,height:20}}/>
                <Skeleton style={{width:400,height:'12vh'}}/>
                <Skeleton style={{width:200,height:20}}/>
            </div>

        </div>
    );
};

export default AlbumPageHeaderSkeleton;