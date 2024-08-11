import {Navigate, Outlet} from "react-router-dom";
import { observer } from "mobx-react-lite";
import {useContext} from "react";
import {Context} from "../index";
import {LOGIN} from "./routes";

const PrivateRoute = () => {
    const {userStore} = useContext(Context)
    if (userStore.isAuth) {
        return <Outlet/>
    } else {
        return <Navigate to={LOGIN} replace/>;
    }
};

export default observer(PrivateRoute);