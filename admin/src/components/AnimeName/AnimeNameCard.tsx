import React from 'react';
import styles from './style.module.css'
import {AnimeNameCardProps} from "../../models/AnimeCards";
import {useNavigate} from "react-router-dom";

const AnimeNameCard = ({name, id} : AnimeNameCardProps) => {
    const navigate = useNavigate()
    return (
        <div className={styles.anime__name__wrapper} onClick={() => navigate('/anime-manage/'+id)}>
            <span>{name}</span>
        </div>
    );
};

export default AnimeNameCard;