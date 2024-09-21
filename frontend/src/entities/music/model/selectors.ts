import {createSelector} from "@reduxjs/toolkit";
import {RootState} from "@/app/store";
import {MusicState} from "@/entities/music";
import {SoundtrackData} from "@/entities/soundtrack";


export const selectMusicState = (state:RootState):MusicState => state.music
export const isTrackSaved = (state: MusicState,id:number):boolean => state.fav_tracks.includes(id)
export const selectCurrentTrack = createSelector(
    [selectMusicState],
    (musicState:MusicState) => {
        const { listening_queue, trackIndex } = musicState;
        if (listening_queue.length > 0 && trackIndex >= 0 && trackIndex < listening_queue.length) {
            return listening_queue[trackIndex].soundtrack;
        }
        return undefined;
    }
);
export const isTrackEquals = (state:MusicState,soundtrackData:SoundtrackData): boolean => {
    return state.listening_queue[state.trackIndex].soundtrack.originalTitle === soundtrackData.originalTitle
}
