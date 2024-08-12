import React from 'react';
import {AnimeTestCardProps} from "../../models/AnimeCards";
import styles from './style.module.css'
const AnimeTestCard = ({title, cardImage} : AnimeTestCardProps) => {
    return (
        <div className={styles.anime__test__card}>
            {cardImage?
                <img src={cardImage} alt=""/>
                : <div style={{width:318,height:200}}></div>
            }
            <span>{title}</span>
        </div>
    );
};

export default AnimeTestCard;