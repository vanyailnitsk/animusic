import React, {useContext} from 'react';
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import Main from "../components/Main/Main";
import AnimePage from '../pages/AnimePage/AnimePage';
import {
    ALBUM_MANAGE,
    ANIME_MANAGE, HOME_ROUTE, LOGIN,
} from "./routes";
import AlbumPage from "../pages/AlbumPage/AlbumPage";
import Homepage from "../pages/Home/Homepage";
import {Context} from "../index";
import Auth from "../Authorization/Auth";
import PrivateRoute from "./privateRoute";

function AppRouter() {
    const {userStore} = useContext(Context)
    return (
        <BrowserRouter>
            <Routes>
                <Route path={LOGIN} element={userStore.isAuth? <Navigate to={HOME_ROUTE} replace /> : <Auth />} />
                {!userStore.isAuthInProgress && (
                    <Route element={<PrivateRoute/>}>
                        <Route path={HOME_ROUTE} element={<Main page={<Homepage/>}/>}/>
                        <Route path={ANIME_MANAGE} element={<Main page={<AnimePage/>}/>}/>
                        <Route path={ALBUM_MANAGE} element={<Main page={<AlbumPage/>}/>}/>
                    </Route>
                )}
            </Routes>
        </BrowserRouter>
    );
}

export default AppRouter;
