import React, { Component } from "react";
import "./AlbumCover.css"

class AlbumCover extends React.Component {

    render() {
        var cover = this.props.cover;
        
        if (cover) {
            console.log("COVER " + cover);
            cover = String(cover).replace("D:\\covers\\", "http://localhost:44144/content/");
        }
        if (cover) {
            return (
                <>
                    <div className="album-cover">
                        <img width="400" height="400" src={cover}></img>
                    </div>
                </>
            );
        } else {
            return (
                <>
                    <div className="album-cover">
                    <img width="400" height="400" src="img/fake-album-cover.png"></img>
                    </div>
                </>
            );
        }
       
    }
}

export default AlbumCover;