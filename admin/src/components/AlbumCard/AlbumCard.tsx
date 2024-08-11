import React, {useState} from 'react';
import "./AlbumCard.css"
import {AlbumCardProps} from "../../models/Albums";
import {storageUrl} from "../../services/api/consts";
const AlbumCard = ({name,id,handleNavigate,image} : AlbumCardProps) => {
    return (
        <div className="album__card__wrapper" onClick={() => handleNavigate(id)}>
            <img src={storageUrl+image} alt=""/>
            <span>{name}</span>
        </div>
    );
};

export default AlbumCard;