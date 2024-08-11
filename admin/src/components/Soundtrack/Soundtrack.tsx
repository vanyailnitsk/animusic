import React, {useContext, useEffect} from "react";
import "./Soundtrack.css";
import addButton from '../../icons/addButton.png'
import {formatTime} from "../../tools/FormatTime";
import {SoundtrackProps} from "../../models/Soundtracks";
import {storageUrl} from "../../services/api/consts";
import {Context} from "../../index";
import {EDIT_SOUNDTRACK} from "../../Forms/vatietyForms";
import {observer} from "mobx-react";


const Soundtrack = ({soundtrackData, index} : SoundtrackProps) => {
    const {contentStore,editSoundtrack} = useContext(Context)
    useEffect(() => {
        editSoundtrack.setTrackId(soundtrackData.id)
    }, []);
    const clickHandler = () => {
        editSoundtrack.setTrackImage('')
        editSoundtrack.setTrackId(soundtrackData.id)
        contentStore.setCurrentContentType(EDIT_SOUNDTRACK)
    }
    const image = storageUrl + (soundtrackData.image?.source || "images/track-img.jpeg")
    return (
        <div className={`soundtrack__container`} onClick={clickHandler}>
            <button className="soundtrack__toggle__play">
                <span>{index+1}</span>
            </button>
            {(editSoundtrack.trackId && (editSoundtrack.trackId === soundtrackData.id) && editSoundtrack.trackImg)?
                <img src={editSoundtrack.trackImg} alt="" className="soundtrack__image"/>
                :<img src={image} alt="" className="soundtrack__image"/>
            }
            <h3 className="title">{soundtrackData.animeTitle}</h3>
            <p className="original__title">{soundtrackData.originalTitle}</p>
            <button className="soundtrack__add">
                <img src={addButton} alt=""/>
            </button>
            <span className='track__duration'>{formatTime(soundtrackData.duration)}</span>
        </div>
    );
};

export default observer(Soundtrack);
