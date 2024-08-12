import React, {useContext} from 'react';
import { observer } from 'mobx-react';
import CreateAnime from "../../Forms/CreateAnime/CreateAnime";
import {Context} from "../../index";
import './ChangeForm.css'
import {
    ADD_SOUNDTRACK,
    CREATE_ALBUM,
    CREATE_ANIME,
    EDIT_ALBUM,
    EDIT_ANIME,
    EDIT_SOUNDTRACK
} from "../../Forms/vatietyForms";
import EditAnime from "../../Forms/EditAnime/EditAnime";
import CreateAlbum from "../../Forms/CreateAlbum/CreateAlbum";
import EditAlbum from "../../Forms/EditAlbum/EditAlbum";
import AddSoundtrack from "../../Forms/AddSoundtrack/AddSoundtrack";
import EditSoundtrack from "../../Forms/EditSoundtrack/EditSoundtrack";


const ChangeForm = observer(() => {
    let form;
    const {contentStore} = useContext(Context)
    switch (contentStore.currentContentType) {
        case CREATE_ANIME:
            form = <CreateAnime />;
            break;
        case EDIT_ANIME:
            form = <EditAnime />;
            break;
        case CREATE_ALBUM:
            form = <CreateAlbum />;
            break;
        case EDIT_ALBUM:
            form = <EditAlbum />;
            break;
        case ADD_SOUNDTRACK:
            form = <AddSoundtrack />;
            break;
        case EDIT_SOUNDTRACK:
            form = <EditSoundtrack />;
            break;
        default:
            form = <CreateAnime />;
    }

    return (
        <div className='change__form__wrapper'>
            {form}
        </div>
    );
});

export default ChangeForm;
