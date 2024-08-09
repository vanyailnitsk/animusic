import styles from './app-content.module.css'
import {Navigation} from "@/widgets/navigation";
import {MediaLibrary} from "@/widgets/media-library";
import {MainContent} from "@/widgets/main-content";
import {FC, ReactNode} from "react";

interface AppContentProps{
    page:ReactNode
}
export const AppContent:FC<AppContentProps> = ({page}) => {
    return (
        <div className={styles.main__wrapper}>
            <Navigation/>
            <MediaLibrary/>
            <MainContent page={page}/>
        </div>
    );
};

