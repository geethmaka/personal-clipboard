import React from 'react';
import { Button, Typography, Grid } from '@mui/material';

const Footer = (props) =>{
    return(
        <div>
            <Grid container
                direction="column"
                justifyContent="center"
                alignItems="center">
                <Grid item xs={6} md={8}>
                    <Typography variant="caption">An open source project made in Sri Lanka --contributed and used by University Students.</Typography>
                </Grid>
            </Grid>
        </div>
    )
}

export default Footer;
