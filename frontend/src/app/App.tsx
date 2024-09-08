import './styles/global.css';
import { useEffect, useState } from "react";
import { AppRouter } from "@/app/routers";
import { SnackbarProvider } from "notistack";
import { useAppDispatch, useAppSelector } from "@/shared/lib/store";
import { checkUserAuth, selectUser, selectUserLoading } from "@/entities/user";
import {fetchCollection} from "@/entities/music";

function App() {
    const userLoading = useAppSelector(selectUserLoading);
    const user = useAppSelector(selectUser);
    const dispatch = useAppDispatch();
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const initializeApp = async () => {
            if (localStorage.getItem('token')) {
                await dispatch(checkUserAuth());
            }
            setLoading(false);
        };

        initializeApp();
    }, [dispatch]);

    useEffect(() => {
        if (user && !userLoading) {
            dispatch(fetchCollection())

        }
    }, [user, userLoading]);

    if (loading) {
        return null;
    }

    return (
        <div className='app'>
            <SnackbarProvider>
                <AppRouter />
            </SnackbarProvider>
        </div>
    );
}

export default App;
