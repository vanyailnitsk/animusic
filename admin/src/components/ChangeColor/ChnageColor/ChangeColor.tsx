import React, {useContext, useState} from 'react';
import Color from "../Color/Color";
import {Context} from "../../../index";
import styles from './style.module.css'
import {HexColorPicker} from "react-colorful";
import EditBannerStore from "../../../store/EditBannerStore";
import EditAlbumStore from "../../../store/EditAlbumStore";

interface ChangeColorProps{
    colors:string[]
    store:EditBannerStore | EditAlbumStore
}
const ChangeColor = ({colors,store} : ChangeColorProps) => {
    const [selectedColor,setSelectedColor]= useState('')
    const ChangeColor = (color:string) => {
        setSelectedColor(color)
        store.setColor(selectedColor)
    }
    return (
        <div className={styles.change__color__wrapper}>
            <div className={styles.colors}>
                {colors.map(color=>
                    <Color color={color} setColor={ChangeColor}/>
                )}
            </div>
            <HexColorPicker color={selectedColor} onChange={ChangeColor} style={{marginBlock:20}}/>
        </div>
    );
};

export default ChangeColor;