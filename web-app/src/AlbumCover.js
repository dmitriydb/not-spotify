import React, { Component } from "react";
import "./AlbumCover.css"

class AlbumCover extends React.Component {

    render() {
        var cover = String(this.props.cover);
    
        //D:\covers\008318e1-9c3b-4fc7-825f-d77ac9c7af1c.jpg
        cover = cover.replace("D:\\covers\\", "http://localhost:44144/content/");
        console.log("Current cover is " + cover);
        return (
            <>
                <div className="album-cover">
                    <img src={cover}></img>
                </div>
            </>
        );
    }
}

export default AlbumCover;