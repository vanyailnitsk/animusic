import {ISoundtrack, SoundtrackData} from "@/entities/soundtrack";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {MusicState} from "@/entities/music/model/types.ts";



const initialState:MusicState = {
    listening_queue: JSON.parse(localStorage.getItem("listening_queue") || '[]'),
    trackIndex: JSON.parse(localStorage.getItem("currentTrackIndex") || '0'),
    isPlaying: false,
    volume: Number(localStorage.getItem('volume')) || 0.5,
    fav_tracks: [],
}

const musicSlice = createSlice({
    name: 'music',
    initialState,
    reducers: {
        setPlaylist: (state:MusicState, action: PayloadAction<ISoundtrack[]>) => {
            state.listening_queue = action.payload
            localStorage.setItem("listening_queue", JSON.stringify(action.payload));
        },
        changeVolume: (state:MusicState, action: PayloadAction<number>) => {
            state.volume = action.payload
            localStorage.setItem('volume', action.payload.toString());
        },
        setTrackIndex: (state,action: PayloadAction<number>) => {
            state.trackIndex = action.payload
            localStorage.setItem("currentTrackIndex", JSON.stringify(action.payload));
        },
        togglePlayPause: (state) => {
            state.isPlaying = !state.isPlaying;
        },
        setIsPlaying: (state,action: PayloadAction<boolean>) =>{
            state.isPlaying = action.payload;
        },
        nextTrack: (state) => {
            const nextIndex = state.trackIndex + 1;
            if (nextIndex < state.listening_queue.length) {
                state.trackIndex++;
            } else {
                state.trackIndex = 0;
            }
        },
        previousTrack: (state) => {
            const previousIndex = state.trackIndex - 1;
            if (previousIndex > 0) {
                state.trackIndex = previousIndex;
            } else {
                state.trackIndex = 0;
            }
        }
    }
})
export default musicSlice.reducer
export const isTrackSaved = (state: MusicState,id:number):boolean => state.fav_tracks.includes(id)
export const currentTrack = (state: MusicState): SoundtrackData | undefined => {
    if (state.listening_queue && state.listening_queue.length > state.trackIndex && state.trackIndex >= 0) {
        return state.listening_queue[state.trackIndex].soundtrack;
    } else {
        return undefined;
    }
}
export const {
    setPlaylist,
    changeVolume,
    setTrackIndex,
    setIsPlaying,
    togglePlayPause,
    nextTrack,
    previousTrack,
} = musicSlice.actions



