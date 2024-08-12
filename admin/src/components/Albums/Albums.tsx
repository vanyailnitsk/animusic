import React, {useContext} from 'react';
import AlbumCard from "../AlbumCard/AlbumCard";
import './Albums.css'
import {AlbumsProps} from "../../models/Albums";
import {Context} from "../../index";
import {CREATE_ALBUM} from "../../Forms/vatietyForms";
const Albums = ({albums,handleNavigate} : AlbumsProps) => {
    const {contentStore} = useContext(Context)
    return (
        <div className="albums">
            {albums.map(album=> (
                <AlbumCard name={album.name} id={album.id} handleNavigate={handleNavigate} key={album.id} image={album.coverArt?.image.source}/>
            ))}
            {albums.length!==4 && <div onClick={() => contentStore.setCurrentContentType(CREATE_ALBUM)} className='create__album'>+</div>}
        </div>
    );
};

export default Albums;