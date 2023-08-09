import chalk from 'chalk';
import fs from 'fs';
import axios from 'axios';
import csvParser from 'csv-parser';
import { POI, Amenity } from './dto.js';

const env = process.argv[2];

if(env != 'local' && env != 'prod'){
    console.error(chalk.red('Must run script with argument local/prod.'))
    process.exit(1);
}

const baseUrl = (env == 'local') ? 'http://localhost:8080/api' : 'https://dallasbymetro.com';

const apiKey = process.env.X_API_KEY; // Read the API key from the environment variable

if (!apiKey) {
  console.error(chalk.red('API key not found in environment variable.'));
  process.exit(1);
}

// Function to make API call for each row
async function makeApiCall(rowData) {
    try {
        // data for request
        const poi = new POI(rowData.poiId, rowData.name, rowData.location,
            rowData.walkingDistance, rowData.picUrl, rowData.type, rowData.amenities.split('_').map(
                (amenityId) => new Amenity(amenityId, "") // process amenities
            ),
            rowData.stationId, rowData.stationName);
    
        // checking if poi exists
        const poiResponse = await axios.get(baseUrl+'/public/poi', {
            params: {
                ID: poi.poiId
            }
        });

        if(poiResponse.status === 200) { // poi exists; should update
            console.log('POI with poiId='+poi.poiId+' exists. Updating.');
            // putting data
            const putResponse = await axios.put(baseUrl+'/private/poi', poi, {
                headers: {
                    'x-api-key': apiKey
                }});
            if(putResponse.status === 200)
                console.log(chalk.green('POI with poiId='+poi.poiId+' updated successfully.'));
        } else if(poiResponse.status < 500) { // poi does not exist; should create
            console.log('POI with poiId='+poi.poiId+' does not exist. Creating.');
            // posting data
            const postResponse = await axios.post(baseUrl+'/private/poi', poi, {
                headers: {
                    'x-api-key': apiKey
                }});
            if(postResponse.status === 200)
                console.log(chalk.green('POI with poiId='+poi.poiId+' created successfully.'));
        }
    } catch(error) {
        console.log(chalk.red(error.message));
        console.log(chalk.red(error.response.data.errorMessage));
    }
}

// Read CSV file and process each row
function readCSVAndMakeApiCall(csvFilePath) {
  fs.createReadStream(csvFilePath)
    .pipe(csvParser())
    .on('data', (row) => {
      makeApiCall(row);
    })
    .on('end', () => {
      console.log(chalk.blue('CSV file successfully processed.'));
    })
    .on('error', (error) => {
      console.error(chalk.red('Error reading CSV file:', error.message));
    });
}

readCSVAndMakeApiCall('data.csv');