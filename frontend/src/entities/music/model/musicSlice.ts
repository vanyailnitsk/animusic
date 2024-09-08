import {IPlaylistSoundtrack, ISoundtrack} from "@/entities/soundtrack";
import {createSlice, PayloadAction} from "@reduxjs/toolkit";
import {addTrackToCollection, fetchCollection, MusicState, removeTrackFromCollection} from "@/entities/music";



const initialState:MusicState = {
    listening_queue: JSON.parse(localStorage.getItem("listening_queue") || '[]'),
    trackIndex: JSON.parse(localStorage.getItem("currentTrackIndex") || '0'),
    isPlaying: false,
    volume: Number(localStorage.getItem('volume')) || 0.5,
    fav_tracks: [],
}

export const musicSlice = createSlice({
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
        },
        clearCollection:(state:MusicState) => {
            state.fav_tracks = []
        }
    },
    extraReducers:(builder) => {
        builder
            .addCase(fetchCollection.fulfilled, (state:MusicState, action:PayloadAction<IPlaylistSoundtrack[]>) => {
                state.fav_tracks = action.payload.map(track => track.soundtrack.id)
            })

            .addCase(removeTrackFromCollection.fulfilled, (state:MusicState, action: PayloadAction<number>) => {
                state.fav_tracks = state.fav_tracks.filter(id => id !== action.payload)
            })

            .addCase(addTrackToCollection.fulfilled, (state:MusicState, action:PayloadAction<number>) => {
                state.fav_tracks.push(action.payload)
            })
    }
})
export default musicSlice.reducer
export const {
    setPlaylist,
    changeVolume,
    setTrackIndex,
    setIsPlaying,
    togglePlayPause,
    nextTrack,
    previousTrack,
    trackEquals,
    clearCollection
} = musicSlice.actions



