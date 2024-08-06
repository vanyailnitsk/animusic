import React from 'react';
import './MediaLibrary.css'
import FavoriteTracks from "../UserPlaylist/FavoriteTracks";

interface MediaLibraryProps {
    menuActive: boolean
}

const MediaLibrary = ({menuActive}: MediaLibraryProps) => {

    return (
        <div className={menuActive ? 'media__library__wrapper menu__active' : 'media__library__wrapper'}>
            <FavoriteTracks />
        </div>
    );
};

export default MediaLibrary;