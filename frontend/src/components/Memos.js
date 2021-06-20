import React, { useState } from 'react'
import { Modal } from '../components/Modal'
import axios from 'axios'
import './memos.css'

export const Memos = React.memo(({ data, token, userId }) => {

  const [isModalOpen, setIsModalOpen] = useState(false)
  const [memoToShow, setMemoToShow] = useState(null)
  const [isEditing, setIsEditing] = useState(false)

  console.log("memos rendered")

  const showEditHandler = (memo) => {
    setIsEditing(true)
    setMemoToShow(memo)
    setIsModalOpen(true)
  }

  const showAddHandler = () => {
    setIsEditing(false)
    setIsModalOpen(true)
    setMemoToShow(null)
  }

  const onMemoClose = () => {
    setMemoToShow(null)
    setIsModalOpen(false)
  }

  const onSaveHandler = (memo) => {
    if (isEditing) {
      updateMemo(memo)
    } else {
      addMemoToDataSource(memo)
    }
  }

  const updateMemo = async (memo) => {
    await axios.post("/api/v1/memos/edit", {
      id: memo.id,
      title: memo.title,
      description: memo.description,
      createdAt: memo.createdAt,
      userId: memo.userId
    }, {
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      }
    }).then((res) => {
      console.log(res.data)
    }, (error) => {
      console.log(error)
    })
  }

  const addMemoToDataSource = async (memo) => {
    await axios.post("/api/v1/memos/add", {
      title: memo.title,
      description: memo.description
    }, {
      headers: {
        "Content-Type": "application/json",
        "Authorization": "Bearer " + token
      }
    }).then(res => {
      console.log(res.data)
    }, error => {
      console.log(error)
    })
  }

  const removeMemo = async (id) => {
    await axios.delete(`/api/v1/memos/remove/${id}`,
      {
        headers: {
          "Content-Type": "application/json",
          "Authorization": "Bearer " + token
        }
      }
    ).then(res => {
      console.log(res.data)
      window.location.reload();
    }, error => {
      console.log(error)
    })
  }

  return (
    <>
      {isModalOpen && <Modal memoToShow={memoToShow} isEditing={isEditing} onSaveHandler={onSaveHandler} onMemoClose={onMemoClose} />}
      {data.map((memo, index) => {
        return (
          <div className="card col-8 col-md-5 col-xl-3 text-center rounded-3 shadow-sm m-3 memo" key={index}>
            <div className="card-body memo-inner">
              <h4 className="card-title text-secondary">{memo.title}</h4>
              <p className="card-text">{memo.description}</p>
              <p className="card-text">{memo.createdAt}</p>
              {token && memo.userId == userId && (
                <div className="card-buttons">
                  <button type="button" className="btn btn-primary text-light me-5" onClick={() => { showEditHandler(memo) }}>Edit</button>
                  <button type="button" className="btn btn-primary text-light" onClick={() => { removeMemo(memo.id) }}>Remove</button>
                </div>
              )}
            </div>
          </div>
        )
      })}
      <div className="row justify-content-center">
        {token && <button
          type="button"
          className="btn btn-primary text-light col-3 mt-5"
          onClick={showAddHandler}>
          Add memo
        </button>}
      </div>
    </>
  )
})
