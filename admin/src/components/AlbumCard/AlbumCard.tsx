import React from 'react';
import "./AlbumCard.css"
import {AlbumCardProps} from "../../models/Albums";

const AlbumCard = ({name,id,handleNavigate,image} : AlbumCardProps) => {
    return (
        <div className="album__card__wrapper" onClick={() => handleNavigate(id)}>
            <img src={image} alt="" />
            <span>{name}</span>
        </div>
    );
};

export default AlbumCard;