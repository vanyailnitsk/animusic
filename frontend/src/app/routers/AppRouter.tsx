import {useContext} from 'react';
import {SkeletonTheme} from 'react-loading-skeleton';
import {BrowserRouter, Navigate, Route, Routes} from 'react-router-dom';
import {observer} from "mobx-react-lite";
import {PrivateRoute} from "@/app/routers/privateRoute.tsx";
import {Context} from "@/main.tsx";
import {MusicPlayer} from "@/widgets/music-player";
import {HomePage} from "@/pages/home";
import {AppContent} from "@/widgets/app-content";
import {AnimePage} from "@/pages/anime";
import {AlbumPage} from "@/pages/album";
import {SearchPage} from "@/pages/search";
import {PlaylistPage} from "@/pages/playlist";
import {SignIn, SignUp} from "@/pages/auth";
import {ALBUM_ROUTE, ANIME_ROUTE, COLLECTION, HOME_ROUTE, LOGIN, SEARCH_ROUTE, SIGN_UP} from "@/shared/consts";

export const AppRouter = observer(() => {
    const {userStore} = useContext(Context)
    return (
        <SkeletonTheme baseColor="#313131" highlightColor="#525252">
            <BrowserRouter>
                {userStore.isAuth && <MusicPlayer/>}
                <Routes>
                    <Route path={LOGIN} element={userStore.isAuth ? <Navigate to={HOME_ROUTE} replace/> : <SignIn/>}/>
                    <Route path={SIGN_UP} element={userStore.isAuth ? <Navigate to={HOME_ROUTE} replace/> : <SignUp/>}/>
                    {!userStore.isAuthInProgress && (
                        <Route element={<PrivateRoute/>}>
                            <Route path={HOME_ROUTE} element={<AppContent page={<HomePage/>}/>}/>
                            <Route path={ANIME_ROUTE} element={<AppContent page={<AnimePage/>}/>}/>
                            <Route path={ALBUM_ROUTE} element={<AppContent page={<AlbumPage/>}/>}/>
                            <Route path={SEARCH_ROUTE} element={<AppContent page={<SearchPage/>}/>}/>
                            <Route path={COLLECTION} element={<AppContent page={<PlaylistPage/>}/>}/>
                        </Route>
                    )}
                </Routes>
            </BrowserRouter>
        </SkeletonTheme>
    );
})

