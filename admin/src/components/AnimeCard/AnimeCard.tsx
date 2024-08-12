import React from 'react';
import './AnimeCard.css'
import {AnimeCardProps} from "../../models/AnimeCards";
import {useNavigate} from "react-router-dom";

const AnimeCard = ({card} : AnimeCardProps) => {
    const navigate = useNavigate()
    const imageUrl = card?.cardImage?.source
    return (
        <div className="anime__card" onClick={() => navigate('/anime-manage/'+card?.id)}>
            <img src={imageUrl} alt=""/>
            <span>{card?.title}</span>
        </div>
    );
};

export default AnimeCard;