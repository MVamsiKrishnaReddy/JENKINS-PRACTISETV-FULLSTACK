import React, { useState, useEffect } from 'react';
import axios from 'axios';
import './style.css';
import config from './config.js';

const TVManager = () => {
  const [tvs, setTvs] = useState([]);
  const [tv, setTv] = useState({
    brand: '',
    model: '',
    screenSize: '',
    resolution: '',
    smart: '',
    price: ''
  });
  const [idToFetch, setIdToFetch] = useState('');
  const [fetchedTv, setFetchedTv] = useState(null);
  const [message, setMessage] = useState('');
  const [editMode, setEditMode] = useState(false);

  const baseUrl = `${config.url}/tvapi`;

  useEffect(() => {
    fetchAllTvs();
  }, []);

  const fetchAllTvs = async () => {
    try {
      const res = await axios.get(`${baseUrl}/all`);
      setTvs(res.data);
    } catch (error) {
      setMessage('Failed to fetch TVs.');
    }
  };

  const handleChange = (e) => {
    setTv({ ...tv, [e.target.name]: e.target.value });
  };

  const validateForm = () => {
    for (let key in tv) {
      if (!tv[key] || tv[key].toString().trim() === '') {
        setMessage(`Please fill out the ${key} field.`);
        return false;
      }
    }
    return true;
  };

  const preparePayload = () => {
    // Convert numeric fields to numbers and exclude id for POST
    return {
      ...tv,
      screenSize: Number(tv.screenSize),
      price: Number(tv.price)
    };
  };

  const addTv = async () => {
    if (!validateForm()) return;
    try {
      const payload = preparePayload();
      await axios.post(`${baseUrl}/add`, payload);
      setMessage('TV added successfully.');
      fetchAllTvs();
      resetForm();
    } catch (error) {
      console.error(error);
      setMessage('Error adding TV: ' + (error.response?.data || error.message));
    }
  };

  const updateTv = async () => {
    if (!validateForm()) return;
    try {
      const payload = {
        ...tv,
        screenSize: Number(tv.screenSize),
        price: Number(tv.price)
      };
      await axios.put(`${baseUrl}/update`, payload);
      setMessage('TV updated successfully.');
      fetchAllTvs();
      resetForm();
    } catch (error) {
      console.error(error);
      setMessage('Error updating TV: ' + (error.response?.data || error.message));
    }
  };

  const deleteTv = async (id) => {
    try {
      const res = await axios.delete(`${baseUrl}/delete/${id}`);
      setMessage(res.data);
      fetchAllTvs();
    } catch (error) {
      console.error(error);
      setMessage('Error deleting TV: ' + (error.response?.data || error.message));
    }
  };

  const getTvById = async () => {
    try {
      const res = await axios.get(`${baseUrl}/get/${idToFetch}`);
      setFetchedTv(res.data);
      setMessage('');
    } catch (error) {
      setFetchedTv(null);
      setMessage('TV not found.');
    }
  };

  const handleEdit = (tvData) => {
    setTv(tvData);
    setEditMode(true);
    setMessage(`Editing TV with ID ${tvData.id}`);
  };

  const resetForm = () => {
    setTv({
      brand: '',
      model: '',
      screenSize: '',
      resolution: '',
      smart: '',
      price: ''
    });
    setEditMode(false);
  };

  return (
    <div className="hospital-container">

      {message && (
        <div className={`message-banner ${message.toLowerCase().includes('error') ? 'error' : 'success'}`}>
          {message}
        </div>
      )}

      <h2>TV Management System</h2>

      <div>
        <h3>{editMode ? 'Edit TV' : 'Add TV'}</h3>
        <div className="form-grid">
          <input type="text" name="brand" placeholder="Brand" value={tv.brand} onChange={handleChange} />
          <input type="text" name="model" placeholder="Model" value={tv.model} onChange={handleChange} />
          <input type="text" name="screenSize" placeholder="Screen Size" value={tv.screenSize} onChange={handleChange} />
          <input type="text" name="resolution" placeholder="Resolution" value={tv.resolution} onChange={handleChange} />
          <input type="text" name="smart" placeholder="Smart TV (Yes/No)" value={tv.smart} onChange={handleChange} />
          <input type="number" name="price" placeholder="Price" value={tv.price} onChange={handleChange} />
        </div>

        <div className="btn-group">
          {!editMode ? (
            <button className="btn-blue" onClick={addTv}>Add TV</button>
          ) : (
            <>
              <button className="btn-green" onClick={updateTv}>Update TV</button>
              <button className="btn-gray" onClick={resetForm}>Cancel</button>
            </>
          )}
        </div>
      </div>

      <div>
        <h3>Get TV By ID</h3>
        <input
          type="number"
          value={idToFetch}
          onChange={(e) => setIdToFetch(e.target.value)}
          placeholder="Enter ID"
        />
        <button className="btn-blue" onClick={getTvById}>Fetch</button>

        {fetchedTv && (
          <div>
            <h4>TV Found:</h4>
            <pre>{JSON.stringify(fetchedTv, null, 2)}</pre>
          </div>
        )}
      </div>

      <div>
        <h3>All TVs</h3>
        {tvs.length === 0 ? (
          <p>No TVs found.</p>
        ) : (
          <div className="table-wrapper">
            <table>
              <thead>
                <tr>
                  {Object.keys(tv).map((key) => (
                    <th key={key}>{key}</th>
                  ))}
                  <th>Actions</th>
                </tr>
              </thead>
              <tbody>
                {tvs.map((tvItem) => (
                  <tr key={tvItem.id}>
                    {Object.keys(tv).map((key) => (
                      <td key={key}>{tvItem[key]}</td>
                    ))}
                    <td>
                      <div className="action-buttons">
                        <button className="btn-green" onClick={() => handleEdit(tvItem)}>Edit</button>
                        <button className="btn-red" onClick={() => deleteTv(tvItem.id)}>Delete</button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </table>
          </div>
        )}
      </div>

    </div>
  );
};

export default TVManager;
