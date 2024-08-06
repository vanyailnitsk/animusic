import React, {MouseEventHandler, useContext} from 'react';
import {storageUrl} from "../../../services/api/consts";
import addButton from "../../../icons/addButton.png";
import {Context} from "../../../main.tsx";
import {useNavigate} from "react-router-dom";

const CurrentTrack = () => {
    const {musicStore} = useContext(Context)
    const navigate = useNavigate()
    const addTrack: MouseEventHandler<HTMLImageElement> = (e) => {
        e.stopPropagation();
        if (musicStore.currentTrack?.id) {
            musicStore.addToFavorite(musicStore.currentTrack.id);
        }
    };
    return (
        <div className={musicStore.currentTrack ? 'current__track' : 'hidden'}>
            <img
                src={musicStore.currentTrack && storageUrl + (musicStore.currentTrack.image?.source || "images/track-img.jpeg")}
                alt=""
                className='track__img'/>
            {musicStore.currentTrack &&
                <div className='track__name'>
                            <span
                                onClick={() => navigate(`/album/${musicStore.currentTrack?.album.id}`)}
                                className={musicStore.currentTrack.originalTitle.length > 20 ? "scrolling" : ""}>{musicStore.currentTrack.originalTitle}</span>
                    <span>{musicStore.currentTrack.animeTitle}</span>
                </div>
            }
            <img src={addButton} alt="" className='add__track' onClick={addTrack}/>
        </div>
    );
};

export default CurrentTrack;