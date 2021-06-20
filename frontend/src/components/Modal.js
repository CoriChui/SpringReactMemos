import React, { useEffect, useState } from 'react';
import './Modal.css'
import ReactDom from 'react-dom'
import { MdClose } from 'react-icons/md';

const initialState = {
  id: "",
  title: "",
  description: "",
  createdAt: "",
  userId: ""
}

export const Modal = ({ memoToShow, isEditing, onSaveHandler, onMemoClose }) => {

  const [memo, setMemo] = useState(initialState)

  useEffect(() => {
    if (isEditing && memoToShow) {
      setMemo(memoToShow)
    } else {
      setMemo(initialState)
    }
  }, [memoToShow, isEditing])


  const onInputChangeHandler = (e) => {
    const { name, value } = e.target
    setMemo({ ...memo, [name]: value })
    console.log(memo)
  }

  console.log("modal rendered")

  return ReactDom.createPortal(
    <>
      <div className="modal-overlay" />
      <div className="modal-memo">
        <div className="modal-inner">
          <MdClose className="modal-close" onClick={onMemoClose} />
          <form className="modal-form row col-lg-8 mx-4 mx-lg-auto justify-content-center text-center" onSubmit={() => { onSaveHandler(memo) }}>
            <div className="mb-3">
              <label htmlFor="Title" className="form-label">Title</label>
              <input
                type="text"
                required
                maxLength="15"
                name="title"
                className="form-control"
                id="Title"
                onChange={onInputChangeHandler}
                value={memo.title} />
            </div>
            <div className="mb-3">
              <label htmlFor="Description" className="form-label">Description</label>
              <input
                type="text"
                required
                maxLength="60"
                name="description"
                className="form-control"
                id="Description"
                onChange={onInputChangeHandler}
                value={memo.description} />
            </div>
            <button type="submit" className="btn btn-primary text-light mt-4 col-8">Save</button>
          </form>
        </div>
      </div>
    </>, document.getElementById('portal')
  )
}
