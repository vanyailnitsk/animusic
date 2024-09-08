import {FC, MouseEventHandler, useContext} from "react";
import {Context} from "@/main.tsx";
import addButton from '@/shared/assets/icons/follow.png';
import {VariantType, useSnackbar} from 'notistack';
import savedTrackImage from '@/shared/assets/icons/saved-track.png';
import {useAppDispatch, useAppSelector} from "@/shared/lib/store";
import {
    addToCollection,
    addTrackToCollection, isTrackSaved,
    removeFromCollection,
    removeTrackFromCollection,
    selectMusicState
} from "@/entities/music";
import {selectUser} from "@/entities/user";

interface SaveTrackProps {
    className: string;
    id: number;
}

export const SaveTrack: FC<SaveTrackProps> = (props) => {
    const {className, id} = props
    const isSaved = useAppSelector(state => isTrackSaved(state.music,id))
    const {enqueueSnackbar} = useSnackbar()
    const dispatch = useAppDispatch()
    const user = useAppSelector(selectUser)

    const handleSavedTrack: MouseEventHandler<HTMLButtonElement> = (e) => {
        e.stopPropagation()
        if(!user){
            handleAddError('error')
        }
        else{
            if (id) {
                if (isSaved) {
                    dispatch(removeTrackFromCollection(id))
                } else {
                   dispatch(addTrackToCollection(id));
                }
            }
        }
    };
    const handleAddError = (variant:VariantType)  => {
        enqueueSnackbar('You need to sign in!', {variant});
    };
    return (
        <button className={className} onClick={handleSavedTrack}>
            {isSaved ? <img src={savedTrackImage} alt=''/> : <img src={addButton} alt=''/>}
        </button>
    );
};

