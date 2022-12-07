import React, { Component } from "react";
import "./Menu.css";
import RecentlyPlayedItem from "./RecentlyPlayedItem";
import Modal from 'react-modal';
import "./Modal.css";

class Menu extends React.Component {

    constructor(props) {
        super(props);
        this.state = { signingUp: false };
        this.closeSignup = this.closeSignup.bind(this);
        this.openSignup = this.openSignup.bind(this);
        this.closeSignin = this.closeSignin.bind(this);
        this.openSignin = this.openSignin.bind(this);

    }

    closeSignup() {
        this.setState({ signingUp: false });
    }

    openSignup() {
        this.setState({ signingUp: true });
    }

    closeSignin() {
        this.setState({ signingIn: false });
    }

    openSignin() {
        this.setState({ signingIn: true });
    }


    render() {
        var recently = [];
        for (var song of this.props.history) {
            recently.push(
                <RecentlyPlayedItem song={song} changeSongCallBack={this.props.changeSongCallBack} />
            )
        }
        return (
            <>
                <div className="menu">
                    <p className="menu-item">Home</p>
                    <p className="menu-item">Search</p>
                    <p className="menu-item" onClick={this.openSignup}>Sign up</p>
                    <p className="menu-item" onClick={this.openSignin}>Sign in</p>
                    <p className="menu-item">Playlists</p>
                    <p className="menu-item">Favorites</p>
                    <hr></hr>
                    <p className="menu-caption">Recently played</p>
                    {recently}
                </div>

                <Modal
                    isOpen={this.state.signingUp}
                    onRequestClose={this.closeSignup}
                    contentLabel="Example Modal"
                    className="modal-content"
                >
                    <label className="my-label" align="center" htmlFor="username"><b>Username</b></label>
                    <input autocomplete="chrome-off" className="my-input" type="text" placeholder="Enter username" name="username" required />
                    <label className="my-label" align="center" htmlFor="psw"><b>Password</b></label>
                    <input data-lpignore="true"
                        autocomplete="chrome-off" className="my-input" type="password" placeholder="Enter password" name="psw" required />
                    <label className="my-label" align="center" htmlFor="psw-repeat"><b>Repeat Password</b></label>
                    <input data-lpignore="true"
                        autocomplete="chrome-off" className="my-input" type="password" placeholder="Repeat password" name="psw-repeat" required />
                    <div className="buttons">
                        <button type="button" className="cancelbtn" onClick={this.closeSignup}>Cancel</button>
                        <button type="submit" className="signupbtn">Sign up</button>
                    </div>

                </Modal>

                <Modal
                    isOpen={this.state.signingIn}
                    onRequestClose={this.closeSignin}
                    contentLabel="Example Modal"
                    className="modal-content"
                >
                    <label className="my-label" align="center" htmlFor="username"><b>Username</b></label>
                    <input autocomplete="chrome-off" className="my-input" type="text" placeholder="Enter username" name="username" required />
                    <label className="my-label" align="center" htmlFor="psw"><b>Password</b></label>
                    <input data-lpignore="true"
                        autocomplete="chrome-off" className="my-input" type="password" placeholder="Enter password" name="psw" required />
                    <div className="buttons">
                        <button type="button" className="cancelbtn" onClick={this.closeSignin}>Cancel</button>
                        <button type="submit" className="signupbtn">Sign in</button>
                    </div>

                </Modal>


            </>
        );
    }
}

export default Menu;