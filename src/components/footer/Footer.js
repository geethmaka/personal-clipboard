import React from 'react';
import { Button, Typography, Grid, Box } from '@mui/material';

const Footer = (props) =>{
    return(
        <div>
            <Grid container
                direction="column"
                justifyContent="center"
                alignItems="center">
                <Grid item xs={6} md={8}>
                    <Box>
                        <Typography variant="caption" display="block"
                        color="textSecondary"
                        variant="caption"
                        align="center">
                        An open source project made in Sri Lanka 
                        --contributed and used by University Students.<br />
                        Original Repository by <a sx={{color: 'text.secondary'}} 
                        href="https://github.com/geethmaka">geethmaka</a>
                        </Typography>
                    </Box>
                </Grid>
            </Grid>
        </div>
    )
}

export default Footer;
