import React from 'react';
import AlbumCard from "../AlbumCard/AlbumCard";
import './Albums.css'
import {AlbumsProps} from "../../models/Albums";

const Albums = ({albums,handleNavigate} : AlbumsProps) => {
    return (
        <div className="albums">
            {albums.map(album=> (
                <AlbumCard name={album.name} id={album.id} handleNavigate={handleNavigate} key={album.id} image={album.coverArt?.image.source}/>
            ))}
        </div>
    );
};

export default Albums;