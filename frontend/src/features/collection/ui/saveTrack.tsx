import {FC, MouseEventHandler, useContext} from "react";
import {Context} from "@/main.tsx";
import addButton from '@/shared/icons/follow.png';
import {VariantType, useSnackbar} from 'notistack';
import savedTrackImage from '@/shared/icons/saved-track.png';

interface SaveTrackProps {
    className: string;
    saved: boolean | undefined
    id: number | undefined;
}

export const SaveTrack: FC<SaveTrackProps> = (props) => {
    const {className, id, saved} = props
    const {enqueueSnackbar} = useSnackbar()
    const {musicStore} = useContext(Context)
    const {userStore} = useContext(Context)

    const handleSavedTrack: MouseEventHandler<HTMLButtonElement> = async (e) => {
        e.stopPropagation()
        if(!userStore.isAuth && !userStore.isAuthInProgress){
            handleAddError('error')
        }
        else{
            if (id) {
                if (saved) {
                    await musicStore.removeFromFavorites(id);
                } else {
                    await musicStore.addToFavorite(id);
                }
            }
        }
    };
    const handleAddError = (variant:VariantType)  => {
        enqueueSnackbar('You need to sign in!', {variant});
    };
    return (
        <button className={className} onClick={handleSavedTrack}>
            {saved ? <img src={savedTrackImage} alt=''/> : <img src={addButton} alt=''/>}
        </button>
    );
};

