import {FC, MouseEventHandler, useContext, useState} from "react";
import {Context} from "@/main.tsx";
import addButton from '@/shared/icons/follow.png';
import savedTrackImage from '@/shared/icons/saved-track.png';
interface SaveTrackProps{
    className:string;
    saved:boolean | undefined
    id:number | undefined;
}
export const SaveTrack:FC<SaveTrackProps> = (props) => {
    const {className,id,saved} = props
    const {musicStore} = useContext(Context)

    const handleSavedTrack: MouseEventHandler<HTMLButtonElement> = async (e) => {
        e.stopPropagation();
            if (id) {
                if (saved) {
                    await musicStore.removeFromFavorites(id);
                } else {
                    await musicStore.addToFavorite(id);
                }
            }
    };
    return (
        <button className={className} onClick={handleSavedTrack}>
            {saved? <img src={savedTrackImage} alt=''/> : <img src={addButton} alt=''/>}
        </button>
    );
};

