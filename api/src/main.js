const express = require('express')
const Sequelize = require('sequelize')
const bodyParser = require('body-parser')
const morgan = require('morgan')
const cors = require('cors')
const app = express()
const sequelize = new Sequelize('glowing-potato-db', 'postgres', '',
  { host: '127.0.0.1', dialect: 'postgres' })

// MODEL

const Post = sequelize.define('post', {
  tags: { type: Sequelize.ARRAY(Sequelize.TEXT), allowNull: false },
  title: { type: Sequelize.TEXT, allowNull: false },
  description: { type: Sequelize.TEXT, allowNull: false },
  body: { type: Sequelize.TEXT, allowNull: false }
})

// ENDPOINT

const pubError = 'Segfault'

app.use(bodyParser.json())
app.use(morgan('tiny'))
app.use(cors())

app.get('/api', (_, res) => res.send('Alive and well.'))

app.get('/api/blog/posts', (req, res) =>
  Post.findAll().then(ps => res.send(ps)).catch(() => res.send(pubError)))

app.get('/api/blog/posts/:id', (req, res) =>
  Post.findById(req.params.id).then(p => res.send(p)).catch(() => res.send(pubError)))

app.post('/api/blog/posts', (req, res) =>
  sequelize.sync()
  .then(() => Post.create(req.body)
    .then(p => res.send(p))
    .catch(e => res.send(e.name)))
  .catch(e => res.send(e)))

app.listen(3000)
