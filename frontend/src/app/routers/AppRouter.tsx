import {SkeletonTheme} from 'react-loading-skeleton';
import {BrowserRouter,Route, Routes} from 'react-router-dom';
import {observer} from "mobx-react-lite";
import {HomePage} from "@/pages/home";
import {AppContent} from "@/widgets/app-content";
import {AnimePage} from "@/pages/anime";
import {AlbumPage} from "@/pages/album";
import {SearchPage} from "@/pages/search";
import {PlaylistPage} from "@/pages/playlist";
import {SignIn, SignUp} from "@/pages/auth";
import {ALBUM_ROUTE, ANIME_ROUTE, COLLECTION, HOME_ROUTE, SEARCH_ROUTE, SIGN_IN, SIGN_UP} from "@/shared/consts";
import {isMobile} from "react-device-detect";
import {PhoneMusicPlayer} from "@/widgets/phone-music-player";
import {PhoneNavigation} from "@/widgets/phone-navigation";
import {MusicPlayer} from "@/entities/music";

export const AppRouter = observer(() => {
    return (
        <SkeletonTheme baseColor="#313131" highlightColor="#525252">
            <BrowserRouter>
                {isMobile? <PhoneMusicPlayer/> : <MusicPlayer/>}
                {isMobile && <PhoneNavigation/>}
                <Routes>
                    <Route path={SIGN_IN} element={<SignIn/>}/>
                    <Route path={SIGN_UP} element={<SignUp/>}/>
                    <Route path={HOME_ROUTE} element={<AppContent page={<HomePage/>}/>}/>
                    <Route path={ANIME_ROUTE} element={<AppContent page={<AnimePage/>}/>}/>
                    <Route path={ALBUM_ROUTE} element={<AppContent page={<AlbumPage/>}/>}/>
                    <Route path={SEARCH_ROUTE} element={<AppContent page={<SearchPage/>}/>}/>
                    <Route path={COLLECTION} element={<AppContent page={<PlaylistPage/>}/>}/>
                </Routes>
            </BrowserRouter>
        </SkeletonTheme>
    );
})

