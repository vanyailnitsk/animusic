import React, {useContext} from 'react';
import {SkeletonTheme} from 'react-loading-skeleton';
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import Main from "../components/Main/Main";
import Homepage from '../pages/Home/Homepage';
import AnimePage from '../pages/AnimePage/AnimePage';
import {ALBUM_ROUTE, ANIME_ROUTE, COLLECTION, HOME_ROUTE, LOGIN, SEARCH_ROUTE, SIGN_UP} from "./routes";
import AlbumPage from "../pages/AlbumPage/AlbumPage";
import MusicPlayer from "../components/MusicPlayer/MusicPlayer";
import SearchPage from "../pages/SearchPage/SearchPage";
import Login from "../components/Login/Login";
import SignUp from "../components/SignUp/SignUp";
import PrivateRoute from "./privateRoute";
import {Context} from "../main.tsx";
import UserPlaylistPage from "../pages/UserPlaylistPage/UserPlaylistPage";
import {observer} from "mobx-react-lite";

function AppRouter() {
    const {userStore} = useContext(Context)
    return (
        <SkeletonTheme baseColor="#313131" highlightColor="#525252">
        <BrowserRouter>
            {userStore.isAuth && <MusicPlayer/>}
                <Routes>
                    <Route path={LOGIN} element={userStore.isAuth? <Navigate to={HOME_ROUTE} replace /> : <Login />} />
                    <Route path={SIGN_UP} element={userStore.isAuth? <Navigate to={HOME_ROUTE} replace /> : <SignUp />} />
                    {!userStore.isAuthInProgress && (
                        <Route element={<PrivateRoute/>}>
                            <Route path={HOME_ROUTE} element={<Main page={<Homepage/>}/>}/>
                            <Route path={ANIME_ROUTE} element={<Main page={<AnimePage/>}/>}/>
                            <Route path={ALBUM_ROUTE} element={<Main page={<AlbumPage/>}/>}/>
                            <Route path={SEARCH_ROUTE} element={<Main page={<SearchPage/>}/>}/>
                            <Route path={COLLECTION} element={<Main page={<UserPlaylistPage/>}/>}/>
                        </Route>
                    )}
                </Routes>
        </BrowserRouter>
        </SkeletonTheme>
    );
}

export default observer(AppRouter);
